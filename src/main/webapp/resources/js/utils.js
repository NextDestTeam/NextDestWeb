function addFooterText(date,price,id,bad,maybe,good,comments){
    var div = document.getElementById(id);
    var d = new Date(date);

    var options = {  year: 'numeric', month: 'short', day: 'numeric' };

    console.log(d.toLocaleDateString(getLang(), options));
    div.innerHTML = "Date: "+d.toLocaleDateString(getLang(), options)+" Cost: $"+price.toFixed(2)+'<br/>'+
        bad+' <span class="glyphicon glyphicon-remove-sign" ></span>\n' +
        maybe+'  <span class="glyphicon glyphicon-question-sign" ></span>\n' +
        good+' <span class="glyphicon glyphicon-ok-sign" ></span>\n' +
        comments+'  <span class="glyphicon glyphicon-comment" ></span>';
}

function getLang()
{
    if (navigator.languages != undefined)
        return navigator.languages[0];
    else
        return navigator.language;
}

function addDate(date,id) {
    d = new Date(date);

    var options = {year: 'numeric', month: 'short', day: 'numeric'};

    document.getElementById(id).innerHTML=d.toLocaleDateString(getLang(), options);
}