"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    document.querySelector("form").addEventListener("submit", displayPlayerInputs);
    document.querySelector('#create-game').addEventListener('click', createNewGame);
    document.querySelector('#join-game').addEventListener('click', joinGame);
}

function displayPlayerInputs(e) {
    e.preventDefault();

    const numberOfPlayers = parseInt(document.querySelector("#number-of-players").value);
    console.log('Chosen number of players:', numberOfPlayers);
    getGames(numberOfPlayers);
}

function createNewGame() {

    const numberOfPlayers = parseInt(document.querySelector('#number-of-players').value);

    fetchFromServer("/games", "POST", {
        "prefix": _config.prefix,
        "numberOfPlayers": numberOfPlayers,
    })
        .then(response =>  {
            console.log("Created new game: ", response);

            //Refresh game list
            savePlayerName();
            getGames(numberOfPlayers);
        })
        .catch(errorHandler);
}
function getGames(numberOfPlayers) {
    fetchFromServer(`/games?started=false&numberOfPlayers=${numberOfPlayers}&prefix=${_config.prefix}`, "GET").then(games => {
        displayGames(games);})
        .catch(errorHandler);
}

function displayGames(games) {

    savePlayerName();
    document.querySelector('#join-game').disabled = false;
    let html = '';
    const $div = document.querySelector(".games-display");
    $div.innerHTML = '';
    games.forEach(game => {
        html += `<div class="ongoing-game" data-gameid="${game.id}">
                    <ul>
                        <li>Max players: ${game.maxPlayers}</li>
                        <li>Players joined ${game.players.length}</li>
                        <li>ID: ${game.id}</li>
                    </ul>
               </div>`;
    });
    $div.insertAdjacentHTML("beforeend", html);
    console.log("All available games:",games);

    addListenersToListedGames();
}

function savePlayerName() {

    const playerName = document.querySelector("#playername").value;
    localStorage.setItem('playerName', playerName);
}

function addListenersToListedGames() {

    document.querySelectorAll('.ongoing-game').forEach(game => {
        game.addEventListener('click', function(e) {
            console.log(e.currentTarget.dataset.gameid);
            const gameId = e.currentTarget.dataset.gameid;
            localStorage.setItem("selectedGameId", gameId);
            console.log('Selected Game Id:', gameId);
        });
    });
    document.querySelectorAll('.ongoing-game').forEach(game => game.addEventListener('click', markSelectedGame));
}

function markSelectedGame(e) {

    const selectedGames = document.querySelectorAll('.selected-game');

    if (selectedGames != null) {
        selectedGames.forEach(el => el.classList.remove('selected-game'));
    }
    e.currentTarget.classList.add('selected-game');
}

async function joinGame() {

    savePlayerName();

    const gameData = {
        gameId: localStorage.getItem('selectedGameId'),
        playerName: localStorage.getItem('playerName')
    };
    await fetchFromServer(`/games/${gameData.gameId}/players`, "POST", { playerName: gameData.playerName })
        .then(token => {
            window._token = token;
            console.log("Join Game token: ", token);
            localStorage.setItem('playerToken', JSON.stringify(token));
        })
        .catch(errorHandler);

    movePlayerToGameScreen();
}

function movePlayerToGameScreen() {

    window.location = 'game.html';
}


