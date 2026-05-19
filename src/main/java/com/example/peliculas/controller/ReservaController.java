package com.example.peliculas.controller;

import java.sql.Connection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.peliculas.dto.CategoriaDisponibleResponse;
import com.example.peliculas.dto.ReservaRequest;
import com.example.peliculas.entity.Reserva;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.ReservaRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
	private final DataSource ds;
    public ReservaController(DataSource ds) {
        this.ds = ds;
    }
    
    @GetMapping("/mis-reservas")
    public List<Reserva> misReservas(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        try (Connection con = ds.getConnection()) {

            ReservaRepository repo = new ReservaRepository(con);
            return repo.findByUserId(userId);

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
  
    
    /*
     * BUSCAR CATEGORÍAS DISPONIBLES
     * GET /api/reservas/disponibles?desde=2026-05-10&hasta=2026-05-15
     */
    @GetMapping("/disponibles")
    public List<CategoriaDisponibleResponse> disponibles(
            @RequestParam String desde,
            @RequestParam String hasta
    ) {
        try (Connection con = ds.getConnection()) {

            ReservaRepository repo = new ReservaRepository(con);

            System.out.println(repo.findDisponibles(desde, hasta));
            
            List<CategoriaDisponibleResponse> categorias =
                    repo.findDisponibles(desde, hasta);

            LocalDate fechaDesde = LocalDate.parse(desde);
            LocalDate fechaHasta = LocalDate.parse(hasta);

            int dias = (int) ChronoUnit.DAYS.between(fechaDesde, fechaHasta);

            return categorias.stream()
                    .map(c -> new CategoriaDisponibleResponse(
                            c.idCategoria(),
                            c.nombre(),
                            c.precioPorDia(),
                            c.precioPorDia() * dias,
                            c.habitacionId()
                    ))
                    .toList();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @PostMapping("/confirmar")
    public void confirmar(
            @RequestBody ReservaRequest req,
            HttpSession session
    ) {
    	
    	LocalDate desde = LocalDate.parse(req.fechaDesde());
    	LocalDate hasta = LocalDate.parse(req.fechaHasta());
    	LocalDate hoy = LocalDate.now();

    	if (desde.isBefore(hoy) || hasta.isBefore(hoy)) {
    	    throw new ResponseStatusException(
    	        HttpStatus.BAD_REQUEST,
    	        "No puedes reservar en el pasado"
    	    );
    	}

    	if (hasta.isBefore(desde) || hasta.equals(desde)) {
    	    throw new ResponseStatusException(
    	        HttpStatus.BAD_REQUEST,
    	        "Fechas inválidas"
    	    );
    	}

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        try (Connection con = ds.getConnection()) {

            ReservaRepository repo = new ReservaRepository(con);

            Reserva reserva = new Reserva(
                    null,
                    userId,
                    req.habId(),
                    LocalDate.parse(req.fechaDesde()),
                    LocalDate.parse(req.fechaHasta()),
                    true,
                    LocalDate.now()
            );
            
            System.out.println(reserva);

            repo.insert(reserva);

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
    
    @DeleteMapping("/{id}")
    public void cancelar(
            @PathVariable int id,
            HttpSession session
    ) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        try (Connection con = ds.getConnection()) {

            ReservaRepository repo = new ReservaRepository(con);

            Reserva reserva = repo.find(id);

            if (reserva == null) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Reserva no encontrada"
                );
            }

            // 👇 impedir borrar reservas de otro usuario
            if (!reserva.getUserId().equals(userId)) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No puedes cancelar esta reserva"
                );
            }

            repo.delete(id);

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @PutMapping("/{id}")
    public Reserva modificar(
            @PathVariable int id,
            @RequestBody Reserva reservaModificada,
            HttpSession session
    ){

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        LocalDate desde = reservaModificada.getFechaDesde();
        LocalDate hasta = reservaModificada.getFechaHasta();
        LocalDate hoy = LocalDate.now();

        if (desde.isBefore(hoy) || hasta.isBefore(hoy)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No puedes reservar en el pasado"
            );
        }

        if (hasta.isBefore(desde) || hasta.equals(desde)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Fechas inválidas"
            );
        }

        try (Connection con = ds.getConnection()) {

            ReservaRepository repo = new ReservaRepository(con);

            Reserva reserva = repo.find(id);

            if (reserva == null) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Reserva no encontrada"
                );
            }

            // 👇 impedir modificar reservas ajenas
            if (!reserva.getUserId().equals(userId)) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No puedes modificar esta reserva"
                );
            }

         // 👇 comprobar solapamiento ignorando ESTA reserva
            boolean ocupada = repo.existsSolapamiento(
            	    reserva.getHabId(),
            	    id,
            	    reservaModificada.getFechaDesde().toString(),
            	    reservaModificada.getFechaHasta().toString()
            	);

            if (ocupada) {

                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La habitación ya está reservada en esas fechas"
                );
            }

            reserva.setFechaDesde(desde);
            reserva.setFechaHasta(hasta);

            repo.update(reserva);

            return reserva;

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
}
