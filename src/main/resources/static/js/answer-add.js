document.addEventListener("DOMContentLoaded", () => {
    disableOption();

})

function disableOption()
{
    const questionId = document.getElementById("question-id").value;
    const checkboxMessage = document.getElementById("checkbox-message");
    const url = `/api/question/${questionId}`;
    const checkbox = document.getElementById("correct-checkbox");

    fetch(url).then(response => response.text())
    .then(data => {
        if(data > 0)
        {
            checkbox.setAttribute("onclick", "return false")
            checkbox.style.opacity = "50%";
            checkboxMessage.classList.remove("hide");
        }});
}
