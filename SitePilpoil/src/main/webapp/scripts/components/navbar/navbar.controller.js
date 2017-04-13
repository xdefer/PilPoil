'use strict';

angular.module('pilpoilApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';
        $scope.account = null;

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
        
		$scope.$on('account', function() {
        	Principal.identity().then(function(account) {
                $scope.account = account;
        	});
        });
    });
