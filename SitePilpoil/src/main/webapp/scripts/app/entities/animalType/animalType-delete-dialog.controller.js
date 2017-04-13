'use strict';

angular.module('pilpoilApp')
	.controller('AnimalTypeDeleteController', function($scope, $uibModalInstance, entity, AnimalType) {

        $scope.animalType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AnimalType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
