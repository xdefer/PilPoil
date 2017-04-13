'use strict';

angular.module('pilpoilwebApp').controller('AnimalDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Animal', 'Ad', 'AnimalType', 'Breed',
        function($scope, $stateParams, $uibModalInstance, entity, Animal, Ad, AnimalType, Breed) {

        $scope.animal = entity;
        $scope.ads = Ad.query();
        $scope.animaltypes = AnimalType.query();
        $scope.breeds = Breed.query();
        $scope.load = function(id) {
            Animal.get({id : id}, function(result) {
                $scope.animal = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pilpoilwebApp:animalUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.animal.id != null) {
                Animal.update($scope.animal, onSaveSuccess, onSaveError);
            } else {
                Animal.save($scope.animal, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
