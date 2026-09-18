
    const params = new URLSearchParams(window.location.search);
    const fejl = params.get("error");
    if (fejl) {
    document.getElementById("fejlbesked").textContent = fejl;
    document.getElementById("fejlbesked").style.display = "block";
}
