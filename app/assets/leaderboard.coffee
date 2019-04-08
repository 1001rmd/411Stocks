$.get "/leaderboard/all", (boards) ->
		$.each boards, (index, board) ->
			$('#boards').append $("<li>").text board.name

