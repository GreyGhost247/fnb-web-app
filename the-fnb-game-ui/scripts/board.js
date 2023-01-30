const Board = {
	async fill() {
		Pits.getWinner();

		const url = baseUrl + "/board";

		const options = {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
				"Access-Control-Allow-Origin": "*",
			},
		};

		const response = await fetch(url, options);
		const data = await response.json();
		Board.render(data);
	},

	async sow(pitId) {
		const id = pitId;
		const url = baseUrl + "/" + id;

		const options = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				"Access-Control-Allow-Origin": "*",
			},
		};

		const response = await fetch(url, options);
		const data = await response.json();

		Board.render(data);
		Move.get();
		Pits.getWinner();
	},

	async render(data) {
		document.getElementById("PLAYER_1_PIT_1").innerHTML = data[0].stones;
		document.getElementById("PLAYER_1_PIT_2").innerHTML = data[1].stones;
		document.getElementById("PLAYER_1_PIT_3").innerHTML = data[2].stones;
		document.getElementById("PLAYER_1_PIT_4").innerHTML = data[3].stones;
		document.getElementById("PLAYER_1_PIT_5").innerHTML = data[4].stones;
		document.getElementById("PLAYER_1_PIT_6").innerHTML = data[5].stones;
		document.getElementById("PLAYER_1_LARGER_PIT").innerHTML = data[6].stones;

		document.getElementById("PLAYER_2_PIT_1").innerHTML = data[7].stones;
		document.getElementById("PLAYER_2_PIT_2").innerHTML = data[8].stones;
		document.getElementById("PLAYER_2_PIT_3").innerHTML = data[9].stones;
		document.getElementById("PLAYER_2_PIT_4").innerHTML = data[10].stones;
		document.getElementById("PLAYER_2_PIT_5").innerHTML = data[11].stones;
		document.getElementById("PLAYER_2_PIT_6").innerHTML = data[12].stones;
		document.getElementById("PLAYER_2_LARGER_PIT").innerHTML = data[13].stones;
	},
};
