const Pits = {
	initialize() {
		Board.fill();
		Move.get();
	},

	async getWinner() {
		const url = baseUrl + "/winner";
		const options = {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
				"Access-Control-Allow-Origin": "*",
			},
		};

		const response = await fetch(url, options);
		const data = await response.json();

		if (data != "UNKNOWN") {
			Modal.toggle();
			let winner;
			let color;

			if (data == "PLAYER_1") {
				winner = "Winner: PLAYER ONE";
				color = "blue";
			} else if (data == "PLAYER_2") {
				winner = "Winner: PLAYER TWO";
				color = "red";
			} else {
				winner = "DRAW";
				color = "#555";
			}
			document.getElementById("winner").innerHTML = winner;
			document.getElementById("winner").style.color = color;
			Pits.getBothLargerPits();
			return;
		}
	},

	async getBothLargerPits() {
		const url = baseUrl + "/larger-pits";
		const options = {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
				"Access-Control-Allow-Origin": "*",
			},
		};

		const response = await fetch(url, options);
		const data = await response.json();

		document.getElementById("larger-pit-one").innerHTML = data[0].stones;
		document.getElementById("larger-pit-one").style.color = "blue";
		document.getElementById("larger-pit-two").innerHTML = data[1].stones;
		document.getElementById("larger-pit-two").style.color = "red";
	},

	async reset() {
		const url = baseUrl + "/reset";
		const options = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				"Access-Control-Allow-Origin": "*",
			},
		};
		await fetch(url, options);
		Modal.toggle();
		this.initialize();
	}
};
