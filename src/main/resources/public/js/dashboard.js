let counter = document.querySelectorAll(".task.completed").length;


const taskElm =document.querySelectorAll(".task");

const progressBar = document.querySelector("#progressBar");

const labelProgressBar = document.querySelector('label[for="progressBar"]');

const toggle =document.querySelector('#vidergives');


toggle.addEventListener("change", toggleChange)

function toggleChange (){
    const currentDate = urlParams.get('date');
    let dateDel = "";
    if (currentDate) {
        dateDel = "&date=" + currentDate;
    }
    if (toggle.checked) {
        window.location.href = "/dashboard?view=alle" + dateDel;
    } else {
        window.location.href = "/dashboard?view=mig" + dateDel;
    }
}

const urlParams = new URLSearchParams(window.location.search);
if (urlParams.get('view') === 'alle') {
    toggle.checked = true;
}



for(const t of taskElm){
    t.addEventListener("click", () =>{


        if(t.classList.contains("completed")){
                return;}


            counter++;
            t.classList.add("task-new-background");
            t.classList.add("completed")

            fetch("/tasks/" + t.dataset.id + "/done", { method: "POST" })
            .then(res => res.json())
                .then(data => {
                    const details = t.querySelector(".taskDetails");
                    details.textContent = "Udført klokken "+data.completedAt;
                });


            const img = t.querySelector("img");
            if (img) {
                img.src = "svg/flueben.svg";
            }
            const initials = t.querySelector(".initials");
            if (initials) {
                const nytBillede = document.createElement("img");
                nytBillede.src = "svg/flueben.svg";
                nytBillede.style.gridArea = "box-1";
                initials.replaceWith(nytBillede);
            }

            const header=t.querySelector(".taskHeader");
            header.classList.add("task-new-text");





            updateProgressBar();




    });
}
updateProgressBar();


function updateProgressBar (){
progressBar.value=counter;
progressBar.max=taskElm.length;
labelProgressBar.textContent=counter+"/"+taskElm.length;

//hvis knap er klikket skal den ikke kunne klikkes igen, eller skal den resettes?

if (counter===taskElm.length){
progressBar.classList.add("newColor");
labelProgressBar.textContent="";
}



}
function skiftDato(valgtDato) {
    const recentView = urlParams.get('view');
    window.location.href = "/dashboard?date=" + valgtDato + "&view=" + recentView;
}
