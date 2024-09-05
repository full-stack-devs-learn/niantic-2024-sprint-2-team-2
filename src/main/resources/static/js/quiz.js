document.addEventListener("DOMContentLoaded", () => {
    loadPage();
});

function loadPage()
{
    const container = document.getElementById("quiz-container");
    const startButton = document.getElementById("start");
    let quizId = 1;
    let questionId = 1;

    startButton.addEventListener("click", () => {
        url = `/quiz/${quizId}/${questionId}`;

        fetch(url).then(response => {
        if(response.status === 200)
        {
            return response.text();
        }
        throw new Error(response);
        }).then(data => {
            container.innerHTML = data;
        }).catch(error => {
            console.log(error)
        });
    })
}