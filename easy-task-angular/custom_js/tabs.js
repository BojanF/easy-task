/**
 * Created by Marijo on 22-Aug-17.
 */

function hideOtherContents(){
  $('#tasks-tab').hide();
  $('#documents-tab').hide();
  $('#comments-tab').hide();
  $('#info-tab').hide();
  $('#stats-tab').hide();
};

$("#tasks").click(function(){
  hideOtherContents();
  $("#tasks-tab").show();
});

$("#documents").click(function(){
  hideOtherContents();
  $("#documents-tab").show();
});

$("#comments").click(function(){
  hideOtherContents();
  $("#comments-tab").show();
});

$("#info").click(function(){
  hideOtherContents();
  $("#info-tab").show();
});

$("#stats").click(function(){
  hideOtherContents();
  $("#stats-tab").show();
});
