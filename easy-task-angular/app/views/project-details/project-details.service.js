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

    var userResource = $resource('http://localhost:8000/api/user/:id', {}, {});
    var projectResource = $resource('http://localhost:8000/api/project/:id', {}, {});
    var tasksResource = $resource('http://localhost:8000/api/project/tasks/:id', {}, {});
    var commentsResource = $resource('http://localhost:8000/api/project/comments/:id', {}, {});
    var documentsResource = $resource('http://localhost:8000/api/project/documents/:id', {}, {});
    var newTaskResource = $resource('http://localhost:8000/api/task/:id', {}, {});
    var newCommentsResource = $resource('http://localhost:8000/api/comment/:id', {}, {});
    var newDocumentResource = $resource('http://localhost:8000/api/document/', {},{
      save: {
        method:'POST',
        headers: {'Content-Type': undefined}
      }
    });
    var documentResource = $resource('http://localhost:8000/api/document/:id', {}, {});

    var service = {
      getUser: getUserFn,
      getProject: getProjectFn,
      getTasks: getTasksFn,
      getComments: getCommentsFn,
      getDocuments: getDocumentsFn,
      insertNewTask: insertNewTaskFn,
      insertNewComment: insertNewCommentFn,
      fileUpload: fileUploadFn
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
      return newTaskResource.save(newTask, function (data){
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
  }


})(angular);
