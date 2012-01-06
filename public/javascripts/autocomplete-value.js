$(function() {
   /*
    * Autocomplete for selecting an entity instance from the server, which
    * provides a label (name) and value (id). This approach stores the
    * selection's value in a hidden input field that is submitted with the
    * form, and puts the selection's label in the original input field, which
    * is not submitted with the form.
    */
   $('input.autocomplete-value').each( function() {
      var $input = $(this);

      // Create a hidden input with the same form control name for the value.
      var controlName = $input.attr('name');
    
      // Set-up the autocomplete widget.
      var serverUrl = $input.data('url');
      $(this).autocomplete({
         source: serverUrl,
         focus: function(event, ui) {
            // Set the text input value to the focused item's label.
            var oldValue = $input.val();
            var tmp = oldValue.split(',');
            var s = "";
            var length = tmp.length;
            tmp.forEach(function(value, key) {
                value = value.trim();
                if (key == 0) {
                    s = value;
                } else if (key < length - 1) {
                    s = s + ", " + value;
                }
            });
            if (length == 0 || length == 1) {
                $input.val(ui.item.label);
            } else {
                $input.val(s + ", " + ui.item.label);
            };
            
            return false;
         },
         select: function(event, ui) {
            // Save the selection item and value in the two inputs.
            var oldValue = $input.val();
            var tmp = oldValue.split(',');
            var s = "";
            var length = tmp.length;
            tmp.forEach(function(value, key) {
                value = value.trim();
                if (key == 0) {
                    s = value;
                } else if (key < length - 1) {
                    s = s + ", " + value;
                }
            });


            if (length == 0 || length == 1) {
                $input.val(ui.item.label);
            } else {
                $input.val(s + ", " + ui.item.label);
            };

            return false;
         }
      });
   });

});
