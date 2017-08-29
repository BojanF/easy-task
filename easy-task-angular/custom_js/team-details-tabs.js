/**
 * Created by Bojan on 8/28/2017.
 */

function hideTeamDetailsTabs() {
  $("#details-tab").hide();
  $("#stats-tab").hide();
  $("#update-tab").hide();
}

$("#details").click(function (){
  hideTeamDetailsTabs();
  $("#details-tab").show();
});

$("#update").click(function (){
  hideTeamDetailsTabs();
  $("#update-tab").show();
});

$("#stats").click(function (){
  hideTeamDetailsTabs();
  $("#stats-tab").show();
});
