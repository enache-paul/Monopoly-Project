"use strict";

document.addEventListener('DOMContentLoaded',init);

function init(){

    fetchTiles();
    checkIfGameHasStarted();
    document.querySelector('.roll-button').addEventListener('click', () => rollDice(_gameInfo.gameId, _gameInfo.playerName));
    document.querySelector('.properties').addEventListener('click', displayPropertiesPopUp);
    document.querySelector('.leave-game').addEventListener('click', () =>  leaveGame(_gameInfo.gameId, _gameInfo.playerName));
}

function fetchTiles(){
    fetchFromServer('/tiles','GET').then(tiles => {
        createBoardHTML(tiles);
        fillTilesWithData(tiles);
    });
}

function createBoardHTML(tiles) {
    tiles.forEach(tile => {

        const $newTile = document.createElement('div');
        const $tileName = document.createElement('p');
        const $currentPlayerPosition = document.createElement('div');

        $newTile.append($tileName, $currentPlayerPosition);

        $newTile.classList.add('tile');
        $newTile.classList.add(`position-${tile.position}`);
        $newTile.classList.add(tile.type.replace(/\s/g, '-'));
        $currentPlayerPosition.classList.add('players-on-tile');

        $tileName.classList.add('tile-name');

        if (tile.type === 'utility' || tile.type === 'street' || tile.type === 'railroad') {
            const $tilePrice = document.createElement('p');
            $tilePrice.classList.add('tile-price');
            $newTile.append($tilePrice);

            const $tileColorBox = document.createElement('div');
            $tileColorBox.classList.add('tile-color-box');
            $newTile.prepend($tileColorBox);
        }
        document.querySelector('.board').append($newTile);
    });
}

function fillTilesWithData(tiles) {

    tiles.forEach(tile => {

        const $currentTile = document.querySelector(`.position-${tile.position}`);
        $currentTile.querySelector('.tile-name').textContent = tile.name;

        if (tile.type === 'utility' || tile.type === 'street' || tile.type === 'railroad') {
            $currentTile.querySelector('.tile-price').textContent = tile.cost;

            if (tile.color !== undefined) {
                $currentTile.querySelector('.tile-color-box').classList.add(tile.color);
            }
        }
    });
}

async function checkIfGameHasStarted() {

    const gameState = await getGameState(_gameInfo.gameId);

    console.log('Checking if the game has started.');

    if (gameState.started) {
        hideWaitingScreen();
        createPlayerBalance(gameState);
        displayPlayers(gameState);
        changeNumberOfTurns(gameState);
        changeCurrentPlayerTurn(gameState);
        await checkIfGameStateChanged();
        console.log('Game started.');
    }
    else {
        console.log('Waiting for players.');
        setTimeout(checkIfGameHasStarted, 1500);
    }
}

function createPlayerBalance(gameState) {

    const playerState = gameState.players.find(el => el.name === _gameInfo.playerName);

    document.querySelector('.user-balance').textContent = playerState.balance;
}

function displayPlayers(gameState) {
    gameState.players.forEach(player => {
        const $newPlayerProfile = document.createElement('div');
        $newPlayerProfile.classList.add('player-profile', `${player.name}-profile`);

        const $newProfileName = document.createElement('p');
        $newProfileName.classList.add('player-profile-name');
        $newProfileName.textContent = `${player.name}`;

        const $newPlayerBalance = document.createElement('p');
        $newPlayerBalance.classList.add('player-balance', `${player.name}-balance`);
        $newPlayerBalance.textContent = `${player.balance}`;

        $newPlayerProfile.append($newProfileName, $newPlayerBalance);
        document.querySelector('.list-profile').append($newPlayerProfile);
    });
}

function changeNumberOfTurns(gameState) {

    _gameInfo.numberOfTurns = gameState.turns.length;
}

function hideWaitingScreen() {

    const $waitingForPlayers =  document.querySelector(".waiting-for-players");

    $waitingForPlayers.classList.add("fade-out");

    setTimeout(() => {
        $waitingForPlayers.classList.add("hidden");
    }, 400);
}

function displayPlayerPositionFeedback(gameState) {

    const $players = gameState.players;

    const playerPositions = document.querySelectorAll(`.player-position`);

    if (playerPositions.length !== 0) {
        document.querySelectorAll('.player-position').forEach(el => el.remove());
    }

    fetchFromServer('/tiles','GET').then(tiles => {
        $players.forEach(player => {

            tiles.forEach(tile => {
               if (player.currentTile === tile.name) {
                   const $playerLocationDiv = document.querySelector(`.position-${tile.position}`);
                   const $p = document.createElement("p");
                   $p.textContent = `${player.name}`;
                   $p.classList.add(`player-position`);
                   $playerLocationDiv.append($p);
               }
            });
        });
    });
}


