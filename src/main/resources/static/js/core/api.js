export const api = {

	async request(url, options = {}) {

		console.log(url);

		const { headers = {}, ...rest } = options;

		const config = {
			credentials: "same-origin",
			...rest,
			headers: {
				"Accept": "application/json",
				...headers
			}
		};

		const response = await fetch(url, config);

		let data = null;

		// 🔥 FIX IMPORTANTE: lectura segura de respuesta
		if (response.status !== 204) {

			const text = await response.text();

			if (text) {
				try {
					data = JSON.parse(text);
				} catch {
					data = { message: text };
				}
			}
		}

		// ❌ manejo de errores consistente
		if (!response.ok) {

			const error = new Error(
				data?.message || `HTTP ${response.status}`
			);

			error.status = response.status;
			error.data = data;

			throw error;
		}

		return data;
	},

	async get(url, options = {}) {
		return this.request(url, options);
	},

	async post(url, data, options = {}) {

		const { headers = {}, ...rest } = options;

		return this.request(url, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				...headers
			},
			body: JSON.stringify(data),
			...rest
		});
	},

	async put(url, data, options = {}) {

		const { headers = {}, ...rest } = options;

		return this.request(url, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
				...headers
			},
			body: JSON.stringify(data),
			...rest
		});
	},

	async delete(url, options = {}) {
		return this.request(url, {
			method: "DELETE",
			...options
		});
	}
};