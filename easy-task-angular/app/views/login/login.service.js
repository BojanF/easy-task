/**
 * Created by Marijo on 02-Sep-17.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('LoginService', LoginServiceFn);

 LoginServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function LoginServiceFn($log, $resource) {
    var loginResource = $resource('https://kostancev.com:8080/api/login/:user', {}, {});
    var registerResource = $resource('https://kostancev.com:8080/api/register/:user', {}, {});

    var service = {
       login: loginFn,
      register:registerFn,
      // getTasksStates: getTasksStatesFn,
      // getActiveTasks: getActiveTasksFn,
      // getUrgentProjects: getUrgentProjectsFn
    };
    return service;

    function loginFn(user){
      return loginResource.save(user).$promise;
    }

    function registerFn(user){
      return registerResource.save(user).$promise;
    }


  }

})(angular);
