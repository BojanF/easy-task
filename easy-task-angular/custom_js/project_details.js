/**
 * Created by Marijo on 24-Aug-17.
 */
$(document).on('click',".removeDocument",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Removing...');
  $(this).prop('disabled',true);
});

$(document).on('click',".removeTask",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Removing...');
  $(this).prop('disabled',true);
});

$(document).on('click',".removeComment",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">');
  $(this).prop('disabled',true);
});
