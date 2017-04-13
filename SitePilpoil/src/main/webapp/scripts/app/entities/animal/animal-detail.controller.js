'use strict';

angular.module('pilpoilApp')
    .controller('AnimalDetailController', function ($scope, $rootScope, $stateParams, entity, Animal, Ad, AnimalType, Breed) {
        $scope.animal = entity;
        $scope.load = function (id) {
            Animal.get({id: id}, function(result) {
                $scope.animal = result;
            });
        };
        var unsubscribe = $rootScope.$on('pilpoilApp:animalUpdate', function(event, result) {
            $scope.animal = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
