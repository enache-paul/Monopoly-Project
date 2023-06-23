const _gameInfo = {
    playerName: localStorage.getItem('playerName'),
    gameId: localStorage.getItem('selectedGameId'),
    numberOfTurns: 0,
    currentPlayerTurn: null
};

function getGameState(gameId) {

    return fetchFromServer(`/games/${gameId}`, 'GET').then(gameState => {
        return gameState;
    });
}

function checkIfPropertyAvailable (gameState) {

    if (gameState.directSale != null && gameState.currentPlayer === _gameInfo.playerName) {
        offerPropertyPurchase(gameState.directSale);
    }
    else { console.log('Direct sale is null'); }
}

function offerPropertyPurchase(propertyName) {

    const $popupBackground = document.createElement('div');
    const $popup = document.createElement('div');

    $popupBackground.classList.add('purchase-popup-background');
    $popup.classList.add('purchase-popup');

    $popup.innerHTML =
    ` <p class="popup-message">Would you like to purchase ${propertyName}?</p>
      <input type="button" class="buy-property-button" value="Buy Property">
      <input type="button" class="refuse-property-button" value="Don't Buy">
    `;

    document.querySelector('body').append($popupBackground);
    document.querySelector('body').append($popup);

    document.querySelector('.buy-property-button').addEventListener('click', () => {
        directBuyProperty(propertyName);
    });

    document.querySelector('.refuse-property-button').addEventListener('click', () => {
        doNotBuyProperty(propertyName);
    });

    console.log(`Wanna buy ${propertyName}?`);
}

async function directBuyProperty(propertyName) {

    await fetchFromServer(`/games/${_gameInfo.gameId}/players/${_gameInfo.playerName}/properties/${propertyName}`, 'POST')
    .then(response => console.log('You bought it:', response));

    updateGameState();
    hidePopup("purchase");
    getGameState(_gameInfo.gameId);
}

function doNotBuyProperty(propertyName) {

    fetchFromServer(`/games/${_gameInfo.gameId}/players/${_gameInfo.playerName}/properties/${propertyName}`, 'DELETE')
    .then(response => console.log('You did not buy.:', response));

    hidePopup("purchase");
    getGameState(_gameInfo.gameId); //TODO: This is never used!
}

function hidePopup(popUpName) {
    document.querySelectorAll(`.${popUpName}-popup-background, .${popUpName}-popup`).forEach(el => {
        el.remove();
    });
}

function rollDice(gameId, playerName) {

    fetchFromServer(`/games/${gameId}/players/${playerName}/dice`, 'POST').then(gameState => {
        console.log(gameState);
    });
}
async function checkIfGameStateChanged() {

    const gameState = await getGameState(_gameInfo.gameId);

    if (gameState.turns.length > _gameInfo.numberOfTurns || gameState.currentPlayer !== _gameInfo.currentPlayerTurn) {
        updateGameState();
        _gameInfo.numberOfTurns = gameState.turns.length;
        checkIfGameStateChanged();
        console.log("Game state has changed.");
    }
    else {
        setTimeout(checkIfGameStateChanged, 1500);
        console.log("Checking if the game state has changed.");
    }
}

async function checkPlayerDebt() {

    const gameState = await getGameState(_gameInfo.gameId);

    const currentUser = gameState.players.find(player => player.name === _gameInfo.playerName);
    const currentUserProperties = currentUser.properties.map(element => element.property);
    console.log('Current user properties:', currentUserProperties);

    gameState.players.forEach(otherPlayer => {
        if(otherPlayer.name !== _gameInfo.playerName) {

            currentUserProperties.forEach(currentUserProperty => {

                if(currentUserProperty === otherPlayer.currentTile){
                    console.log(`Collecting money from ${otherPlayer.name} for stepping on ${currentUserProperty}`);
                    collectDebt(currentUserProperty, otherPlayer.name);
                }
            });
        }
    });
}

function collectDebt(propertyName, debtorName) {

    fetchFromServer(
        `/games/${_gameInfo.gameId}/players/${_gameInfo.playerName}/properties/${propertyName}/visitors/${debtorName}/rent`,
        'DELETE').then(response => {
            console.log('Collected successfuly?:', response);
            updateGameState();
        });
}

async function updateGameState() {

    const gameState = await getGameState(_gameInfo.gameId);
    displayDiceResults(gameState);
    displayPlayerPositionFeedback(gameState);
    displayPropertyOwnershipOnBoard(gameState);
    checkIfPropertyAvailable(gameState);
    updatePlayerBalance(gameState);
    checkBankruptcy(gameState);

    if (gameState.currentPlayer !== _gameInfo.currentPlayerTurn) {
        _gameInfo.currentPlayerTurn = gameState.currentPlayer;
        changeCurrentPlayerTurn(gameState);
    }
}

function updatePlayerBalance (gameState) {

    const playerState = gameState.players.find(el => el.name === _gameInfo.playerName);

    document.querySelector('.user-balance').textContent = playerState.balance;

    gameState.players.forEach(player => {
        document.querySelector(`.${player.name}-balance`).textContent = player.balance;
    });
}

function displayDiceResults(gameState) {

    const diceRolls = gameState.previousRoll;

    if (gameState.lastDiceRoll !== null) {

        let res = '';

        diceRolls.forEach(roll => {
            res = res + roll;
            res = res + ' ';
        });

        document.querySelector('.roll-result').textContent = res;
    }
}

function changeCurrentPlayerTurn(gameState) {

    const currentPlayer = document.querySelectorAll('.current-player-turn');

    if (currentPlayer.length !== 0) {
        currentPlayer.forEach(el => el.classList.remove('current-player-turn'));
    }
    document.querySelector(`.${gameState.currentPlayer}-profile`).classList.add('current-player-turn');

}

function checkBankruptcy(gameState) {

    const player = gameState.players.find(currentPlayer => currentPlayer.name === _gameInfo.playerName);

    if (player.money <= 0) {
        bankruptPlayer(_gameInfo.gameId, _gameInfo.playerName);
    }
}

function leaveGame(gameId, playerName) {
    bankruptPlayer(gameId, playerName);
    window.location = "index.html";
}

function bankruptPlayer(gameId, playerName) {

    fetchFromServer(`/games/${gameId}/players/${playerName}/bankruptcy`, 'POST').then(response =>{
        console.log(response);
    });
}

async function displayPropertyOwnershipOnBoard(gameState) {

    fetchFromServer('/tiles','GET').then(tiles => {

        gameState.players.forEach(player => player.properties.forEach(property => {
            const tilePosition = tiles.find(tile => tile.name === property.name);
            const firstLetterOfName = player.name.substring(0, 1);

            document.querySelector(`.position-${tilePosition.position}`)
            .querySelector('.tile-name').textContent = `${tilePosition.name} [${firstLetterOfName}]`;
        }));
    });

}

async function displayPropertiesPopUp() {

    const game = await getGameState(_gameInfo.gameId);
    const $popupBackground = document.createElement('div');
    const $popUp = document.createElement('div');

    $popupBackground.classList.add('properties-popup-background');
    $popUp.classList.add('properties-popup');

    document.querySelector('body').append($popupBackground);
    document.querySelector('body').append($popUp);

    const currentUser = game.players.find(player => player.name === _gameInfo.playerName);
    const currentUserProperties = currentUser.properties;


    let html = '';
    currentUserProperties.forEach(property => html += `<li><span>${property.name}</span> rent: ${property.rent} cost: ${property.cost}</li>`)
    $popUp.insertAdjacentHTML('beforeend', html);

    $popUp.insertAdjacentHTML('beforeend', `<input type="button" value="Close window" class="close-popup-window">`);
    $popUp.insertAdjacentHTML('afterbegin', `<h2 class="popup-message">Purchased Properties</h2>`)
    document.querySelector('.close-popup-window').addEventListener('click', () => hidePopup("properties"));
}
