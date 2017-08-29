/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('ProjectDetailsController', ProjectDetailsController);

  ProjectDetailsController.$inject = ['$log', '$location', 'ProjectDetailsService','$scope','$stateParams'];

  /* @ngInject */
  function ProjectDetailsController($log, $location, ProjectDetailsService,$scope,$stateParams) {
    var vm = this;
    var imageFormats=["png","jpg","jpeg"];
    var textFormats=["txt","c","java"];
    var zipFormats=["zip","rar","7z"];
    //variables declaration
    vm.title = 'project-details';
    vm.PROJECT_ID = 0;
    vm.path="";
    vm.USER_ID = 1; //jas go stavam fiksno
    vm.user = {};
    vm.file={};
    vm.project = {};
    vm.c3DataTest = {
      donuts: {
        points: {
          tasks:[
            {"Not started": 0},
            {"In progress": 0},
            {"Finish": 0},
            {"Deadline braech": 0}
          ]
        },
        columns: {
          tasks:[
            {"id": "Not started", "type": "donut", "color": "#5bc0de"},
            {"id": "In progress", "type": "donut", "color": "#f0ad4e"},
            {"id": "Finish", "type": "donut", "color": "#5cb85c"},
            {"id": "Deadline braech", "type": "donut", "color": "#d9534f"}
          ]
        }
      }
    };
    vm.newEntities = {
      task:{},
      comment:{},
      document:{}
    };

    vm.entitiesData = {
      tasks: [],
      comments: [],
      documents:[]
    };

    vm.uiState = {
      showProject: false,
      leader: false, //addNewButton

      addNewTask:{
        // button: false,
        users: false,
        successMsg: null,
        errorMsg: null
      },
      addNewDocument:{
        successMsg:null,
        errorMsg:null
      },
      tasks:{
        loadGif: true,
        showTasks: false,
        showNoTasksPanel: false,
        showErrorPanel: false,
        deleteError: null
      },
      documents:{
        loadGif: true,
        showDocuments: false,
        showNoDocumentsPanel: false,
        loaded: false,
        showErrorPanel: false
      },
      comments:{
        loadGif: false,
        showComments: false,
        showNoCommentsPanel: false,
        loaded: false,
        errorMsg: null,
        successMsg: null,
        showErrorPanel: false,
        canComment: false
      },
      showStats: false,
      deleteError:{
        task: null,
        document: null
      }
    };

    // vm.inputDates = null;

    vm.datesRestriction = {};
    //functions declaration

    vm.getDocuments = getDocumentsFn;
    vm.getComments = getCommentsFn;
    vm.getStats = getStatsFn;

    vm.saveNewTask = saveNewTaskFn;
    vm.clearNewTask = clearNewTaskFn;
    vm.saveNewComment = saveNewCommentFn;
    vm.saveNewDocument = saveNewDocumentFn;
    vm.clearNewDocument = clearNewDocumentFn;
    //refresh functions
    vm.refreshComments = refreshCommentsFn;
    vm.refreshTasks = refreshTasksFn;
    vm.refreshDocuments = refreshDocumentsFn;

    vm.removeTask = removeTaskFn;
    vm.removeDocument = removeDocumentFn;
    vm.removeComment = removeCommentFn;


    //functions invocation
    getProjectIdFn();


    //functions implementation

    function getProjectIdFn(){
      vm.PROJECT_ID = $stateParams.projectID;
      vm.path=$location.absUrl();
      if (!/^\d+$/.test(vm.PROJECT_ID) || vm.PROJECT_ID.toString()=='0') {
        vm.uiState.showProject = false;
        console.log("OOOPS msg");
      }
      else{
        vm.uiState.showProject = true;
        console.log("show");
        getProjectFn();
        getUser(vm.USER_ID);

      }
      console.log(vm.uiState.showProject);
    }

    function getProjectFn(){
      // getProjectIdFn();
      console.log("asd");
      ProjectDetailsService.getProject(vm.PROJECT_ID).then(

        function (data) {
          //success callback

          vm.project = data;

          if(vm.project.id != undefined){
            console.log('loadiraj');
            getTasksFn();

            projectParsing(vm.project);

            //dateRestriction start
            vm.datesRestriction.createdOn = moment(new Date(vm.project.createdOn));
            vm.datesRestriction.deadline = moment(new Date(vm.project.deadline));
            //dateRestriction end

            if(vm.USER_ID == vm.project.team.leader.user.id.toString()){
              vm.uiState.leader = true;

              console.log(vm.project.team.users.length);

              //ovoj if vajda, 99%, e vishok :)
              if(vm.project.team.users.length > 0) {
                console.log("proektot ima useri na nego");
                vm.uiState.addNewTask.users = true;
              }
            }
          }
          else{
            vm.uiState.showProject = false;
            console.log('oops msg');
          }

        },
        function(){
          //error callback

          vm.uiState.tasks.loadGif = false;
          vm.uiState.tasks.showTasks = false;
          vm.uiState.tasks.showNoTasksPanel = false;
          vm.uiState.tasks.showErrorPanel = true;
        })
    }

    function getTasksFn(){
      vm.uiState.addNewTask.successMsg = null;
      vm.uiState.addNewTask.errorMsg = null;

      ProjectDetailsService.getTasks(vm.PROJECT_ID).then(successTasks, errorTasks);

      function successTasks(data) {
        vm.entitiesData.tasks = data;

        vm.uiState.tasks.loadGif = false;
        vm.uiState.tasks.showErrorPanel = false;
        if(vm.entitiesData.tasks.length > 0){
          vm.uiState.tasks.showTasks = true;
          vm.uiState.tasks.showNoTasksPanel = false;
          threeInOneFunction(vm.entitiesData.tasks);
          vm.uiState.showStats = true;

          vm.project.tasksNum = vm.entitiesData.tasks.length;
        }
        else{
          vm.uiState.tasks.showTasks = false;
          vm.uiState.tasks.showNoTasksPanel = true;
          vm.project.tasksNum = 0;

          vm.project.state = 'CREATED';
          vm.project.cssClass = 'label label-default';
          vm.project.stateString = 'CREATED';
        }
      }

      function errorTasks(){
        vm.uiState.tasks.loadGif = false;
        vm.uiState.tasks.showTasks = false;
        vm.uiState.tasks.showNoTasksPanel = false;
        vm.uiState.tasks.showErrorPanel = true;
      }
    }

    function getUser(userId) {
      ProjectDetailsService.getUser(userId).then(successUser, errorComment);

      function successUser(data) {
        vm.user = data;
        vm.uiState.comments.canComment = true;
      }

      function errorComment(){
        vm.uiState.comments.canComment = false;
      }
    }

    function getDocumentsFn(){

      if(!vm.uiState.documents.loaded){
        vm.uiState.documents.loaded = true;
        vm.uiState.documents.loadGif = true;
        console.log("fetch documents");

        ProjectDetailsService.getDocuments(vm.PROJECT_ID).then(function (data) {

          vm.entitiesData.documents = data;


          vm.uiState.documents.loadGif = false;
          vm.uiState.documents.showErrorPanel = false;
          if(vm.entitiesData.documents.length > 0){
            vm.uiState.documents.showDocuments = true;
            vm.uiState.documents.showNoDocumentsPanel = false;
            setDates(vm.entitiesData.documents);
            vm.project.documentsNum = vm.entitiesData.documents.length;
          }
          else{
            vm.uiState.documents.showDocuments = false;
            vm.uiState.documents.showNoDocumentsPanel = true;
            vm.project.documentsNum = 0;
          }


        }, function(){
          vm.uiState.documents.loadGif = false;
          vm.uiState.documents.showDocuments = false;
          vm.uiState.documents.showNoDocumentsPanel = false;
          vm.uiState.documents.showErrorPanel = true;
        })
      }
    }

    function getCommentsFn(){
      if(!vm.uiState.comments.loaded){
        vm.uiState.comments.loaded = true;
        vm.uiState.comments.loadGif = true;
        console.log("fetch comments");

        ProjectDetailsService.getComments(vm.PROJECT_ID).then(function (data) {

          vm.entitiesData.comments = data;
          vm.uiState.comments.loadGif = false;
          vm.uiState.comments.showErrorPanel = false;
          if(vm.entitiesData.comments.length > 0){
            vm.uiState.comments.showComments = true;
            vm.uiState.comments.showNoCommentsPanel = false;
            setDates(vm.entitiesData.comments);
            vm.project.commentsNum = vm.entitiesData.comments.length;
          }
          else{
            vm.uiState.comments.showComments = false;
            vm.uiState.comments.showNoCommentsPanel = true;
            vm.project.commentsNum = 0;
          }


        }, function(){
          vm.uiState.comments.loadGif = false;
          vm.uiState.comments.showComments = false;
          vm.uiState.comments.showNoCommentsPanel = false;
          vm.uiState.comments.showErrorPanel = true;
        })
      }
    }

    function getStatsFn() {
      console.log("stats");
    }


    function saveNewTaskFn(){
      // console.log(vm.inputDates);
      vm.newEntities.task.project = vm.project;
      vm.newEntities.task.leader = vm.project.team.leader;
      // vm.newEntities.task.createdOn =vm.inputDates.createdOn.toDate().getTime();
      // vm.newEntities.task.deadline = vm.inputDates.deadline.toDate().getTime();
      vm.newEntities.task.createdOn =vm.newEntities.task.createdOn.toDate().getTime();
      vm.newEntities.task.deadline = vm.newEntities.task.deadline.toDate().getTime();
      vm.newEntities.task.state = 'NOT_STARTED';

      vm.uiState.addNewTask.successMsg = null;
      vm.uiState.addNewTask.errorMsg = null;

      console.log(vm.newEntities.task);
      ProjectDetailsService.insertNewTask(vm.newEntities.task).then(successCallbackNewTask, errorCallbackNewTask);

      function successCallbackNewTask(data) {
        console.log("SAVEEEEE");
        $("#modalTask").modal('hide');
        vm.project = data.project;
        projectParsing(vm.project);
        clearNewTaskFn();
        refreshTasksFn();
        vm.uiState.addNewTask.successMsg = "Successfully created \"" + data.name + "\" task!"
      }

      function errorCallbackNewTask() {
        vm.uiState.addNewTask.errorMsg = "Try again later";
      }
    }

    function saveNewDocumentFn(){
      vm.uiState.addNewDocument.errorMsg = null;
      vm.file = $scope.myFile;
      ProjectDetailsService.fileUpload(vm.file, vm.project.id,vm.USER_ID).then(successCallbackNewTask, errorCallbackNewTask);;

      function successCallbackNewTask(data) {
        console.log("SAVEEEEE");
        $("#modalDoc").modal('hide');
        $("#fileUploading").hide();
        $('#file_name').val(" ");
        $('#file').val(null);
        clearNewDocumentFn();
        refreshDocumentsFn();
        $('#documentFormCancel').prop('disabled',false);
        vm.uiState.addNewDocument.successMsg = "Successfully uploaded \"" + vm.file.name + "\""
      }

      function errorCallbackNewTask() {
        $("#fileUploading").hide();
        $('#documentFormCancel').prop('disabled',false);
        vm.uiState.addNewDocument.errorMsg = "Cannot upload, please try again.";
      }
    }

    function clearNewTaskFn(){
      vm.newEntities.task = null;
      // vm.inputDates = null;
      vm.uiState.addNewTask.errorMsg = null;
    }

    function clearNewDocumentFn(){
      vm.newEntities.document = null;
      vm.uiState.addNewDocument.errorMsg = null;


    }

    function saveNewCommentFn(){
      $("#savingComment").show();
      vm.uiState.comments.errorMsg = null;
      vm.uiState.comments.successMsg = null;

      var d = new Date();
      d.getMilliseconds();
      var date = new Date(d);
      vm.newEntities.comment.date = date;
      vm.newEntities.comment.user = vm.user;
      vm.newEntities.comment.project = vm.project;
      console.log(vm.newEntities.comment);
      var promise = ProjectDetailsService.insertNewComment(vm.newEntities.comment);
      console.log(promise);
      promise.then(successCallback, errorCallback);

      function successCallback(data){
        $("#savingComment").hide();
        refreshCommentsFn();
        vm.uiState.comments.successMsg = 'Successfully submitted comment!'; //true
        vm.newEntities.comment = {};
      }

      function errorCallback(data) {
        $("#savingComment").hide();
        vm.uiState.comments.errorMsg = 'Try again to comment!'; //true
      }
    }

    //refresh functions
    function refreshCommentsFn(){
      console.log("refresh comments");
      vm.uiState.comments.errorMsg = null;
      vm.uiState.comments.successMsg = null;
      vm.uiState.comments.loaded = false;
      vm.uiState.comments.showComments = false;
      vm.uiState.comments.showNoCommentsPanel = false;
      vm.uiState.comments.showErrorPanel = false;
      getCommentsFn();
    }

    function refreshTasksFn(){
      console.log("refresh tasks");
      vm.uiState.tasks = {loadGif:true, showTasks:false, showNoTasksPanel:false, showErrorPanel: false, deleteError: null};
      vm.uiState.addNewTask.successMsg = null;

      getTasksFn();
    }

    function refreshDocumentsFn(){
      console.log("refresh documents");
      vm.uiState.documents = {loadGif:true, showDocuments:false, showNoDocumentsPanel:false, showErrorPanel: false};
      getDocumentsFn();
    }

    function removeDocumentFn(documentId, userId){

      if(userId!=vm.USER_ID && !vm.uiState.leader){
          //disable delete button

        var button = $(".removeDocument");
        setTimeout(function(){
          button.html('<i class="fa fa-times"></i>&nbsp;Remove document');
          button.prop('disabled', true);
          console.log("DISABLE DELETE DOC");
        }, 300);


      }
      else{
        ProjectDetailsService.removeDocument(documentId).then(

          function(){
            //success callback
            vm.uiState.addNewDocument.successMsg = "File removed";
            refreshDocumentsFn();
          },
          function () {
            //error callback
            console.log("meeeeeeeh");
            var button = $(".removeDocument");
            setTimeout(function(){
              button.html('<i class="fa fa-times"></i>&nbsp;Remove document');
              button.prop('disabled', true);
              console.log("DISABLE DELETE DOC");
            }, 300);
          }
        );
      }

    }

    function removeTaskFn(taskId){
      console.log(taskId);
      if(vm.uiState.leader) {
        vm.uiState.addNewTask.successMsg = null;
        vm.uiState.tasks.deleteError = null;
        ProjectDetailsService.removeTask(taskId).then(
          function () {
            //success callback

            refreshTasksFn();
            vm.uiState.addNewTask.successMsg = "Successfully deleted task!";
          },
          function () {
            //error callback
            //refreshTasksFn();
            console.log("neuspesno brisenje task");
            //vm.uiState.addNewTask.errorMsg = "Try again";
            vm.uiState.tasks.deleteError = "Try later to delete the task!";
            var button = $(".removeTask");

            setTimeout(function(){
              button.html('<i class="fa fa-times"></i>&nbsp; Delete task');
              button.prop('disabled',true);
              console.log("DISABLE DELETE TASK");
            }, 300);
          }
        );
      }
      else{
        var button = $(".removeTask");
        setTimeout(function(){
          button.html('<i class="fa fa-times"></i>&nbsp; Delete task');
          button.prop('disabled',true);
          console.log("DISABLE DELETE TASK");
        }, 300);

      }
    }

    function removeCommentFn(commentId, userId){

      if(userId!=vm.USER_ID && !vm.uiState.leader){
        //disable delete button
        var button = $(".removeComment");
        setTimeout(function(){
          button.html('<i class="fa fa-times"></i>');
          button.prop('disabled',true);
          console.log("DISABLE DELETE COMMENT");
        }, 300);

      }
      else{
        ProjectDetailsService.removeComment(commentId).then(

          function(){
            //success callback
            refreshCommentsFn();
            vm.uiState.comments.successMsg = "Successfully deleted comment!";
          },
          function(){
            //error callback
            vm.uiState.comments.errorMsg = "Try again later!";
            var button = $(".removeComment");
            setTimeout(function(){
              button.html('<i class="fa fa-times"></i>');
              button.prop('disabled',true);
              console.log("DISABLE DELETE COMMENT");
            }, 300);
          }

        );
      }
    }

    //helper functions

    function threeInOneFunction(tasks){
      //set css class for task state
      //Date milliseconds to Date string
      //calculate stats

      var size = tasks.length;
      var notStarted = 0;
      var inProgress = 0;
      var finished = 0;
      var breach  = 0;

      for(var i=0 ; i<size ; i++){
        var currTask = tasks[i];

        currTask.createdOn = dateMillisecondsToDate(currTask.createdOn);
        currTask.completedOn = dateMillisecondsToDate(currTask.completedOn);
        currTask.deadline = dateMillisecondsToDate(currTask.deadline);

        if(currTask.state == 'NOT_STARTED'){
          currTask.cssClass = 'label label-info';
          currTask.state = 'NOT STARTED';
          notStarted++;
        }
        else if(currTask.state == 'IN_PROGRESS'){
          currTask.cssClass = 'label label-warning';
          currTask.state = 'IN PROGRESS';
          inProgress++;
        }
        else if(currTask.state == 'FINISHED'){
          currTask.cssClass = 'label label-success';
          finished++;
        }
        else{
          currTask.cssClass = 'label label-danger';
          currTask.state = 'BREACH OF DEADLINE';
          breach++;
        }

        tasks[i] = currTask;
      }

      vm.c3DataTest.donuts.points.tasks[0]["Not started"] = notStarted;
      vm.c3DataTest.donuts.points.tasks[1]["In progress"] = inProgress;
      vm.c3DataTest.donuts.points.tasks[2]["Finish"] = finished;
      vm.c3DataTest.donuts.points.tasks[3]["Deadline braech"] = breach;

    }

    function setDates(list){

      var size = list.length;

      for(var i=0 ; i<size ; i++){
        list[i].date = dateMillisecondsToDate(list[i].date);
      }


    }

    function dateMillisecondsToDate(milliseconds){
      console.log(milliseconds);
      if(milliseconds != null) {
        var d = new Date(milliseconds);
        var month = parseInt(d.getMonth()) + 1;
        var minutes = d.getMinutes();
        if (minutes.toString().length == 1) {
          minutes = '0' + minutes;
        }
        return d.getDate() + "." +
          month + "." +
          d.getFullYear() + " " +
          d.getHours() + ":" +
          minutes;
      }
      else{
        return "Not finished yet";
      }

    }

    function projectParsing(project){

      project.createdOnString = dateMillisecondsToDate(project.createdOn);
      project.deadlineString = dateMillisecondsToDate(project.deadline);
      project.completedOnString = dateMillisecondsToDate(project.completedOn);

      if(project.state == 'CREATED'){
        project.cssClass = 'label label-default';
        project.stateString = 'CREATED';
      }
      else if(project.state == 'NOT_STARTED'){
        project.cssClass = 'label label-info';
        project.stateString = 'NOT STARTED';
      }
      else if(project.state == 'IN_PROGRESS'){
        project.cssClass = 'label label-warning';
        project.stateString = 'IN PROGRESS';
      }
      else if(project.state == 'UP_TO_DATE'){
        project.cssClass = 'label label-primary';
        project.stateString = 'UP TO DATE';
      }
      else if(project.state == 'FINISHED'){
        project.cssClass = 'label label-success';
        project.stateString = 'FINISHED';

      }
      else{
        project.cssClass = 'label label-danger';
        project.stateString = 'BREACH OF DEADLINE';
      }

    }



  }


})(angular);
