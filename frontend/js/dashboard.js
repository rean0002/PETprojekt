let counter=0;

const taskElm =document.querySelectorAll(".task");

const progressBar = document.querySelector("#progressBar");

const labelProgressBar = document.querySelector('label[for="progressBar"]');


for(const t of taskElm){
    t.addEventListener("click", () =>{
        counter++;
        console.log(counter);
        updateProgressBar();
    });
}

function updateProgressBar (){
progressBar.value=counter;
progressBar.max=taskElm.length;
labelProgressBar.textContent=counter+"/"+taskElm.length;

if (counter===taskElm.length){
progressBar.classList.add("newColor");
console.log("4/4");
}

}
