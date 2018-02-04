import axios from 'axios';
import classes from './main.css';

const tournamentInput = document.getElementById('tournament-id');
const getResultsBtn = document.getElementById('get-results');
const resultsTable = document.getElementById('results-table');

getResultsBtn.onclick = () => {
    getResultsBtn.classList.add('is-loading');
    axios.get('/tournament/' + tournamentInput.value)
        .then((response) => processData(response.data))
        .finally(() => getResultsBtn.classList.remove('is-loading'));
};

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