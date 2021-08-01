$(document).ready(function() {

    $(".edit").click(function() {
        var $this = $(this);
        var text = $this.text();

        if(text=="Edit") {
            $this.text("Cancel");
        } else {
            $this.text("Edit");
        }
        $(".editable").toggle();
    });

    $("input.editable").change(function() {
        $(this).prev().text($(this).text());
    });

});