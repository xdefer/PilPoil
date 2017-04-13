'use strict';

angular.module('pilpoilApp').controller('FindpetController', function ($scope, $window, Principal, User, Ad, NgMap, BreedByAnimalType, AnimalType, Animal) {
	
	$scope.GENDERS = ["Male", "Femelle"];
	$scope.AGES = ["Jeune", "Adulte"];
	$scope.ADTYPE_TROUVE_ID = 2;
	$scope.FRANCE = "France";

	$scope.animalTypes = AnimalType.query();
	$scope.currentAdress = {};
	$scope.adToSave = {
			user: null,
			recover: false
	}

	$scope.pageState = {
		optionalFieldPanel : false
	}
	
	$scope.updatingPetPicture = "";
	$scope.displayFormPicture = true;
	$scope.updatePetPicture = function(path){
		$scope.displayFormPicture = false;
		$scope.updatingPetPicture = path;
	}
	$scope.removePetPicture = function(){
		$scope.updatingPetPicture = "";
		$scope.displayFormPicture = true;
		$scope.adToSave.animal.photo = null;
	}

	$scope.deployOptionalField = function(event){
    	$scope.pageState.optionalFieldPanel = $scope.pageState.optionalFieldPanel ? false : true;
	}
	
	// Pour que MaterialBootstrap capte les données sinon problème de is-empty
	$scope.showForm = false;
	
	/*function getCurrentLocation(pos) {
		if(pos.coords){
			var geocoder = new google.maps.Geocoder();
	        var latlng = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude);
	        geocoder.geocode({ 'latLng': latlng }, function (results, status) {
	        	if (status == google.maps.GeocoderStatus.OK) {
	        		if (results[0]) {
	        			$scope.currentAdress.adress = results[0].formatted_address;
	        			$scope.currentAdress.country =  $scope.FRANCE;
	            		$scope.currentAdress.lat = results[0].geometry.location.lat();
	        			$scope.currentAdress.lng = results[0].geometry.location.lng();
	        			alert($scope.currentAdress.adress);
	        		} else { alert('Aucune adresse correspondante'); }
	        	} else { alert('Une erreur est survenue'); }
	        });
		}
	}
	
	function errorLocation(){
		alert("error");
	}*/
	
	NgMap.getMap().then(function(map) {
		//navigator.geolocation.getCurrentPosition(getCurrentLocation, errorLocation);
		$scope.map = map;
	});
	
	//https://github.com/allenhwkim/angularjs-google-maps
	$scope.placeChanged = function() {
		$scope.place = this.getPlace();
		if($scope.place && $scope.place.geometry){
			$scope.map.setZoom(15);
			$scope.currentAdress.adress = $scope.place.formatted_address;
			$scope.currentAdress.country =  $scope.FRANCE;
			$scope.currentAdress.lat = $scope.place.geometry.location.lat();
			$scope.currentAdress.lng = $scope.place.geometry.location.lng();
			$scope.map.setCenter($scope.place.geometry.location);
		} else { alert("Adresse incorrecte"); }
	}
	
	// Récupération du user
	Principal.identity().then(function(account) {
		if(account != null){ // Si user connecté
	        User.get({login: account.login}, function success(result){ 
	        	$scope.adToSave.user = result; 
	        	$scope.adToSave.phone = $scope.adToSave.user.phone;
	        	$scope.adToSave.email = $scope.adToSave.user.email;
	        	$scope.showForm = true;
	        });
		} else { 
        	$scope.showForm = true;
		}
    });
	
	$scope.loadBreed = function(animalType){
		if(!animalType){ $scope.breeds = null; } 
		else { BreedByAnimalType.get({id: animalType.id}, function success(result){ $scope.breeds = result; }); }
	}
	
	$scope.setAdGeoloc = function(){
		$scope.adToSave.adress = $scope.currentAdress.adress;
		$scope.adToSave.country = $scope.currentAdress.country;
		$scope.adToSave.longitude = $scope.currentAdress.lat;
		$scope.adToSave.lattitude = $scope.currentAdress.lng;
	}
	
	$scope.saveAd = function(){
		if (!$scope.currentAdress.adress || !$scope.currentAdress.lat || !$scope.currentAdress.lng){
			alert("Adresse incorrecte");
		} else {
			Animal.save($scope.adToSave.animal, function success(result){
				$scope.adToSave.adType = { id: $scope.ADTYPE_TROUVE_ID }
				$scope.adToSave.animal = result;
				$scope.setAdGeoloc();
				Ad.save($scope.adToSave, function success(result){
					alert("Alerte enregistrée");
					$window.location.reload();
				});
			});
		}
	}
});
