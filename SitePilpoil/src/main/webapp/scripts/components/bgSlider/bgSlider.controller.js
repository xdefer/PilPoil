'use strict';

angular.module('pilpoilApp')
    .controller('bgSliderController', function ($scope, $location) {
        $scope.initSlider = function(){
            $('#bgSlider').slick({
                autoplay : true,
                autoplaySpeed : 8000,
                speed: 1200,
                arrows : false,
                draggable : false,
                mobileFirst : true,
                zIndex : 1,
                fade: true
            });
        };

        $scope.initSlider();
    });