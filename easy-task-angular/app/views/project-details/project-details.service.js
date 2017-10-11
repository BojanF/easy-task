/**
 * Created by Bojan on 8/11/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .directive('fileModel', ['$parse', function ($parse) {
      return {
        restrict: 'A',
        link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;

          element.bind('change', function(){
            scope.$apply(function(){
              modelSetter(scope, element[0].files[0]);
            });
          });
        }
      };
    }])
    .factory('ProjectDetailsService', ProjectDetailsServiceFn);

  ProjectDetailsServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function ProjectDetailsServiceFn($log, $resource) {

    var userResource = $resource('https://kostancev.com:8080/api/user/:id', {}, {});
    var projectResource = $resource('https://kostancev.com:8080/api/project/:id', {}, {});
    var tasksResource = $resource('https://kostancev.com:8080/api/project/tasks/:id', {}, {});
    var commentsResource = $resource('https://kostancev.com:8080/api/project/comments/:id', {}, {});
    var documentsResource = $resource('https://kostancev.com:8080/api/project/documents/:id', {}, {});
    var taskResource = $resource('https://kostancev.com:8080/api/task/:id', {}, {});
    var newCommentsResource = $resource('https://kostancev.com:8080/api/comment/:id', {}, {});
    var newDocumentResource = $resource('https://kostancev.com:8080/api/document/', {},{
      save: {
        method:'POST',
        headers: {'Content-Type': undefined}
      }
    });
    var documentResource = $resource('https://kostancev.com:8080/api/document/:id', {}, {});
    var projectUpdateResource = $resource('https://kostancev.com:8080/api/project/update:id', {}, {});

    var service = {
      getUser: getUserFn,
      getProject: getProjectFn,
      getTasks: getTasksFn,
      getComments: getCommentsFn,
      getDocuments: getDocumentsFn,
      insertNewTask: insertNewTaskFn,
      insertNewComment: insertNewCommentFn,
      fileUpload: fileUploadFn,
      removeDocument: removeDocumentFn,
      removeTask: removeTaskFn,
      removeComment: removeCommentFn,
      updateProject: updateProjectFn
    };
    return service;

    function getUserFn(userId){
      return userResource.get({id:userId}).$promise;
    }

    function getProjectFn(projectId){
      return projectResource.get({id:projectId}).$promise;
    }

    function getTasksFn(projectId){
      return tasksResource.query({id:projectId}).$promise;
    }

    function getCommentsFn(projectId){
      return commentsResource.query({id:projectId}).$promise;
    }

    function getDocumentsFn(projectId) {
      return documentsResource.query({id:projectId}).$promise;
    }

    function insertNewTaskFn(newTask){
      console.log("PD service");
      return taskResource.save(newTask, function (data){
        newTask.id = data.id;

      }).$promise;
    }

    function insertNewCommentFn(newComment){

      return newCommentsResource.save(newComment, function (data) {
        newComment.id = data.id;
      }).$promise;

    }

    function fileUploadFn(file, project,user){
        var fd = new FormData();
         fd.append('file', file);
         fd.append('project',project);
         fd.append('user',user);
      return newDocumentResource.save(fd).$promise;
    }

    function removeDocumentFn(document_id){
      return documentResource.delete({id:document_id}).$promise;
    }

    function removeTaskFn(taskId){
      return taskResource.delete({id:taskId}).$promise;
    }

    function removeCommentFn(commentId){
      return newCommentsResource.delete({id:commentId}).$promise;
    }

    function updateProjectFn(project){
      return projectUpdateResource.save(project).$promise;
    }
  }


})(angular);
