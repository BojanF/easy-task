/**
 * Created by Bojan on 9/4/2017.
 */
$(document).on('click',".removeCoworker",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Removing...');
  $(this).prop('disabled',true);
});

$(document).on('click',".cancelRequest",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Canceling...');
  $(this).prop('disabled',true);
});

$(document).on('click',".acceptRequest",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Saving...');
  $(this).prop('disabled',true);
});

$(document).on('click',".refuseRequest",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Turning down...');
  $(this).prop('disabled',true);
});
