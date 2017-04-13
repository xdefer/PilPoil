'use strict';

angular.module('pilpoilwebApp')
    .controller('AnimalTypeDetailController', function ($scope, $rootScope, $stateParams, entity, AnimalType, Animal, Breed) {
        $scope.animalType = entity;
        $scope.load = function (id) {
            AnimalType.get({id: id}, function(result) {
                $scope.animalType = result;
            });
        };
        var unsubscribe = $rootScope.$on('pilpoilwebApp:animalTypeUpdate', function(event, result) {
            $scope.animalType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
