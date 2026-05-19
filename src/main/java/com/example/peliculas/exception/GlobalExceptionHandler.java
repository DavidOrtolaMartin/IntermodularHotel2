package com.example.peliculas.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	// VALIDACIÓN -> 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		logError(ex);
		Map<String, String> errors = new LinkedHashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		Map<String, Object> response = new HashMap<>();
		response.put("errors", errors);

		return ResponseEntity.badRequest().body(response);
	}
	
	// DUPLICATE KEY -> 409
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateKeyException e) {
    	logError(e);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "El recurso ya existe");

        return ResponseEntity.status(409).body(response);
    }

    // DATA ACCESS -> 500
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleData(DataAccessException e) {
    	logError(e);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Error de acceso a datos");

        return ResponseEntity.status(500).body(response);
    }
    
    @ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneric(Exception e) {
		logError(e);
		return ResponseEntity.status(500).body(Map.of("message", "Error interno del servidor"));
	}
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleStatus(ResponseStatusException e) {

        logError(e);

        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getReason());

        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

	private void logError(Exception e) {
		System.err.println(e.getMessage());
		e.printStackTrace();
	}
    
}