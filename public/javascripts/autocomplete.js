/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(function(){
    $('input.autocomplete').each( function() {
        var $input = $(this);
        var serverUrl = $input.data('url');
        $input.autocomplete({
            source:serverUrl
        });
    });
});

