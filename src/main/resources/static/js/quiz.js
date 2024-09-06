document.addEventListener("DOMContentLoaded", () => {
    loadPage();
    calculateScore();
});


function loadPage()
{
    const container = document.getElementById("quiz-container");
    const startButton = document.getElementById("start");
    let quizId = 1;
    let questionId = 1;

    // when start button is clicked
    startButton.addEventListener("click", () => {
        url = `/quiz/${quizId}/${questionId}`;

        startButton.classList.add("hide");

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

function calculateScore() {
    let score = 0;
    const form = document.getElementById("quiz-form");

//    let selection = 0;

    // when submit button is clicked
    form.addEventListener("submit", (event) => {
        event.preventDefault();

//        const selection = document.querySelector("input[name="answers"]:checked")
        let selection = event.target.value;
        if (selection) {
            score++;
        }
    })

    // use fetch to get the Answer
//    const url = `quiz/answer/${selection}`;
//
////    // use fetch to get the isCorrect value
////    fetch(url).then(response => {
////    if(response)
////    }).then(data => {
////        data.isCorrect
////    })

}