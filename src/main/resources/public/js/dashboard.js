let counter = document.querySelectorAll(".task.completed").length;


const taskElm =document.querySelectorAll(".task");

const progressBar = document.querySelector("#progressBar");

const labelProgressBar = document.querySelector('label[for="progressBar"]');

const toggle =document.querySelector('#vidergives');


toggle.addEventListener("change", toggleChange)

function toggleChange (){
    if (toggle.checked) {
        window.location.href = "/dashboard?view=alle";
    } else {
        window.location.href = "/dashboard?view=mig";
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

            fetch("/tasks/" + t.dataset.id + "/done", { method: "POST" });


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
