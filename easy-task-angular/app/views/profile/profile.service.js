/**
 * Created by Marijo on 10-Sep-17.
 */
/**
 * Created by Marijo on 02-Sep-17.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('ProfileService', ProfileServiceFn);

  ProfileServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function ProfileServiceFn($log, $resource) {
    var userResource = $resource('https://kostancev.com:8080/api/user/:id', {}, {});
    var editUserResource = $resource('https://kostancev.com:8080/api/user/:id',{},{save: {
      method:'PUT',
    }}
    );

    var service = {
      findById: findByIdFn,
      updateUser:updateUserFn,
      deactivate:deactivateFn

    };
    return service;

    function findByIdFn(userId){
      return userResource.get({id:userId}).$promise;
    }

    function updateUserFn(user){
      return editUserResource.save({id:user.id},user).$promise;
    }

    function deactivateFn(user){
      return editUserResource.save({id:user.id},user).$promise;
    }
  }

})(angular);
