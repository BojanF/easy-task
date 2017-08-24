/**
 * Created by Marijo on 24-Aug-17.
 */
$(document).on('click',".removeButtons",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Removing...')
  $(this).prop('disabled',true);
});
