'use strict';

angular.module('pilPoilApp')
    .controller('AdDetailController', function ($scope, $rootScope, $stateParams, entity, Ad, AdType, Animal) {
        $scope.ad = entity;
        $scope.load = function (id) {
            Ad.get({id: id}, function(result) {
                $scope.ad = result;
            });
        };
        var unsubscribe = $rootScope.$on('pilPoilApp:adUpdate', function(event, result) {
            $scope.ad = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
