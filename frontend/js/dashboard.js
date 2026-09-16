let counter=0;

const taskElm =document.querySelectorAll(".task");

const progressBar = document.querySelector("#progressBar");

const labelProgressBar = document.querySelector('label[for="progressBar"]');


for(const t of taskElm){
    t.addEventListener("click", () =>{


        if(t.classList.contains("completed")){

        } else {
            counter++;
            console.log(counter);
            t.classList.add("task-new-background");
            t.classList.add("completed")

            const img= t.querySelector("img");
            img.src="svg/flueben.svg";

            const header=t.querySelector(".taskHeader");
            header.classList.add("task-new-text");


            updateProgressBar();

        }


    });
}

function updateProgressBar (){
progressBar.value=counter;
progressBar.max=taskElm.length;
labelProgressBar.textContent=counter+"/"+taskElm.length;

//hvis knap er klikket skal den ikke kunne klikkes igen, eller skal den resettes?

if (counter===taskElm.length){
progressBar.classList.add("newColor");
labelProgressBar.textContent="";
console.log("4/4");
}

}
