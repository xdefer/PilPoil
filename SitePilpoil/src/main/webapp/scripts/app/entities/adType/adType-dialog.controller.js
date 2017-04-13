'use strict';

angular.module('pilpoilApp').controller('AdTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'AdType', 'Ad',
        function($scope, $stateParams, $uibModalInstance, entity, AdType, Ad) {

        $scope.adType = entity;
        $scope.ads = Ad.query();
        $scope.load = function(id) {
            AdType.get({id : id}, function(result) {
                $scope.adType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pilpoilApp:adTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.adType.id != null) {
                AdType.update($scope.adType, onSaveSuccess, onSaveError);
            } else {
                AdType.save($scope.adType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
