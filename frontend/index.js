import axios from 'axios';

const tournamentInput = document.getElementById('tournament-id');
const getResultsBtn = document.getElementById('get-results');
const resultsTable = document.getElementById('results-table');
const errorNotification = document.getElementById('error-notification');
const tournamentLinksContainer = document.getElementById('tournament-links-container');
let isLoading = false;

getResultsBtn.onclick = () => {
    getResultsBtn.classList.add('is-loading');
    errorNotification.classList.add('is-hidden');
    isLoading = true;
    updateFavicon();
    axios.get('/tournament/' + tournamentInput.value)
        .then((response) => processData(response.data))
        .catch(() => errorNotification.classList.remove('is-hidden'))
        .finally(() => {
            isLoading = false;
            updateFavicon();
            getResultsBtn.classList.remove('is-loading');
        });
};

makeTournamentsList();

function makeTournamentsList() {
    const jsonData = document.getElementById('tournaments').textContent;
    const tournamentsData = JSON.parse(jsonData);
    if (tournamentsData.length) {
        tournamentLinksContainer.classList.remove('is-hidden');
        tournamentLinksContainer.querySelector('div').innerHTML = tournamentsData.map(makeTournamentLink).join('<br/>');
        tournamentLinksContainer.onclick = e => {
            const tournamentId = e.target.dataset.tournamentId;
            if (tournamentId && !isLoading) {
                tournamentInput.value = tournamentId;
            }
        }
    }
}

function makeTournamentLink(tournament) {
    return `<a href="#" data-tournament-id="${tournament.id}">${tournament.id} ${tournament.name}</a>`;
}

function processData(data) {
    if (data.length) {
        resultsTable.classList.remove('is-hidden');
        const body = resultsTable.querySelector('tbody');
        body.innerHTML = data.map(makeTeamRow).join('');
    } else {
        resultsTable.classList.add('is-hidden');
    }
}

function makeTeamRow(team) {
    return `
    <tr>
        <td>${team.id}</td>
        <td>${team.name}</td>
        <td>${team.city}</td>
        <td>${team.rating}</td>
        <td>${team.predictedPlace}</td>
        <td>${team.place}</td>
        <td>${team.bonus}</td>
        <td>${team.realBonus}</td>
        <td>${team.points}</td>
    </tr>
    `;
}

function updateFavicon() {
    if (isLoading) {
        changeFavicon('loading.7ac8eff2.png');
    } else {
        changeFavicon('favicon.8870348a.ico');
    }
}

function changeFavicon(src) {
    let link = document.createElement('link');
    let oldLink = document.getElementById('favicon-link');

    link.id = 'favicon-link';
    link.rel = 'shortcut icon';
    link.href = src;

    if (oldLink) {
        document.head.removeChild(oldLink);
    }

    document.head.appendChild(link);
}