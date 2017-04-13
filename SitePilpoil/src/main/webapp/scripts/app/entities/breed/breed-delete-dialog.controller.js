'use strict';

angular.module('pilpoilApp')
	.controller('BreedDeleteController', function($scope, $uibModalInstance, entity, Breed) {

        $scope.breed = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Breed.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
