/**
 * Created by Bojan on 8/11/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('ProjectDetailsService', ProjectDetailsServiceFn);

  ProjectDetailsServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function ProjectDetailsServiceFn($log, $resource) {

    var userResource = $resource('http://localhost:8000/api/user/:id', {}, {});
    var projectResource = $resource('http://localhost:8000/api/project/:id', {}, {});
    var tasksResource = $resource('http://localhost:8000/api/project/tasks/:id', {}, {});
    var commentsResource = $resource('http://localhost:8000/api/project/comments/:id', {}, {});
    var projectsResource = $resource('http://localhost:8000/api/project/documents/:id', {}, {});
    var newTaskResource = $resource('http://localhost:8000/api/task/:id', {}, {});
    var newCommentsResource = $resource('http://localhost:8000/api/comment/:id', {}, {});

    var service = {
      getUser: getUserFn,
      getProject: getProjectFn,
      getTasks: getTasksFn,
      getComments: getCommentsFn,
      getDocuments: getDocumentsFn,
      insertNewTask: insertNewTaskFn,
      insertNewComment: insertNewCommentFn
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
      return projectsResource.query({id:projectId}).$promise;
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
  }

})(angular);
