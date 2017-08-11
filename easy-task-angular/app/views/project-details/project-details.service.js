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

    var projectResource = $resource('http://localhost:8000/api/project/:id', {}, {});
    var tasksResource = $resource('http://localhost:8000/api/project/tasks/:id', {}, {});
    var commentsResource = $resource('http://localhost:8000/api/project/comments/:id', {}, {});
    var projectsResource = $resource('http://localhost:8000/api/project/documents/:id', {}, {});

    var service = {
      getProject: getProjectFn,
      getTasks: getTasksFn,
      getComments: getCommentsFn,
      getDocuments: getDocumentsFn
    };
    return service;

    function getProjectFn(projectId){
      return projectResource.get({id:projectId}).$promise;
    }

    function getTasksFn(projectId){
      return tasksResource.query({id:projectId}).$promise;
    }

    function getCommentsFn(){
      return commentsResource.query({id:projectId}).$promise;
    }

    function getDocumentsFn(projectId) {
      return projectsResource.query({id:projectId}).$promise;
    }

  }

})(angular);
