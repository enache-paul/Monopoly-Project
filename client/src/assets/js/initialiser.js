"use strict";

if (localStorage.getItem('playerToken') !== null) {
    window._token = JSON.parse(localStorage.getItem('playerToken'));
}
else {
    window._token = null;
}

document.addEventListener('DOMContentLoaded',init);

function init(){
    testConnection();
}

function testConnection(){
    fetchFromServer('/tiles','GET').then(tiles => console.log('Connection with API established.'));
}
