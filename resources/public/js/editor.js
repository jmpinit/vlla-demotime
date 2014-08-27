function init() {
    for(var i=0; i < vlla.width * vlla.height; i++) {
        vlla.pixels[i] = [0, 0, 0];
    }

    update();

    $('#editor').data('editor', 'javascript');

    $('textarea[data-editor]').each(function () {
        var textarea = $(this);

        var mode = textarea.data('editor');

        var editDiv = $('<div>', {
            position: 'absolute',
            width: textarea.width(),
            height: textarea.height(),
            'class': textarea.attr('class')
        }).insertBefore(textarea);

        textarea.css('visibility', 'hidden');

        var editor = ace.edit(editDiv[0]);
        editor.renderer.setShowGutter(false);
        editor.getSession().setValue(textarea.val());
        editor.getSession().setMode("ace/mode/" + mode);
        // editor.setTheme("ace/theme/idle_fingers");

        // copy back to textarea on form submit...
        textarea.closest('form').submit(function () {
            textarea.val(editor.getSession().getValue());
        })
    });
}

width = 60;
height = 32;
color = [255, 255, 255];
vlla = {
    scale: 10,
    width: 60,
    height: 32,
    pixels: []
}

function paint(x, y) {
    if(x >= 0 && x < vlla.width && y >= 0 && y < vlla.height) {
        vlla.pixels[y*vlla.width+x] = color;
    }
}

function color(r, g, b) {
    color = [r, g, b];
}

function update() {
    var canvas = $('#viewport')[0];
    var ctx = canvas.getContext('2d');

    for(var y=0; y < vlla.height; y++) {
        for(var x=0; x < vlla.width; x++) {
            var c = vlla.pixels[y*vlla.width+x];
            ctx.fillStyle = 'rgb(' + c[0] + ',' + c[1] + ',' + c[2] + ')';
            ctx.fillRect(x*vlla.scale, y*vlla.scale, vlla.scale, vlla.scale);
        }
    }
}
