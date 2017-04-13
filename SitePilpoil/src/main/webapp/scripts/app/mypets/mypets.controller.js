'use strict';

angular.module('pilpoilApp').controller('MypetsController', function ($scope, Principal, AnimalByUser, Animal, AdsByAnimal, ArchiveAd, User, AnimalType, BreedByAnimalType) {
	
	$scope.pageState = {
		viewMode : false,
		createMode : false,
		currentPet : null,
		currentPetSaveEdit : null,
		editMode : false
	};
	

	$scope.animalTypes = AnimalType.query();
	
	$scope.AGES = ["Jeune", "Adulte"];
	$scope.GENDERS = ["Mâle", "Femelle"];
	
	$scope.loadAll = function(){
		Principal.identity().then(function(account) {
			$scope.account = account;
	        $scope.isAuthenticated = Principal.isAuthenticated;
	        User.get({login: $scope.account.login}, function success(result){
	        	$scope.currentUser = result;
	        	AnimalByUser.get({id:$scope.currentUser.id}, function success(result){
	        		$scope.myPets = result;
	        	});
	        });
	    });
	}
		
	// Init
	$scope.loadAll();
	
	$scope.initPageState = function(){
		$scope.pageState.viewMode = false;
		$scope.pageState.editMode = false;
		$scope.pageState.createMode = false;
		$scope.pageState.currentPet = null;
		$scope.pageState.currentPetSaveEdit = null;
	}
	
	$scope.isLost = function(ads){
		$scope.petIsLost = false;
		angular.forEach(ads, function(ad){
			if(!$scope.petIsLost) {
				if(ad.adType.label == 'Perdu'){ $scope.petIsLost = true; }
			}
		});
	}
	
	$scope.displayPetDetails = function(pet){

		// Si on click sur le même
		if ($scope.pageState.currentPet && $scope.pageState.currentPet.id == pet.id && $scope.pageState.viewMode) {
			$scope.initPageState();
			$scope.pageState.currentPet = null;
			return false;
		}

		$scope.pageState.viewMode = true;
		$scope.pageState.editMode = false;
		$scope.pageState.createMode = false;
		AdsByAnimal.get({id:pet.id}, function success(result){
			$scope.currentPetAds = result;
			$scope.petIsLost = false;
			$scope.isLost($scope.currentPetAds);
			$scope.pageState.viewMode = true;
			$scope.pageState.currentPet = pet;
		});

		Animal.get({id:pet.id}, function success(result){
			$scope.pageState.currentPetSaveEdit = result;
			if($scope.pageState.currentPetSaveEdit.animalType) {
				BreedByAnimalType.get({id: $scope.pageState.currentPetSaveEdit.animalType.id}, function success(result){
					$scope.breeds = result;
				});	
			};
		});
	}
	
	$scope.closeView = function(){
		$scope.initPageState();
	}
	
	$scope.editView = function(petId){

		if ($scope.pageState.editMode)
			return false;

		$scope.pageState.viewMode = false;
		$scope.pageState.editMode = true;
	}
	
	$scope.backToView = function(){
		$scope.pageState.viewMode = true;
		$scope.pageState.editMode = false;
		$scope.pageState.currentPetSaveEdit = null;
	}
	
	$scope.loadBreed = function(animalType){
		if(!animalType){ $scope.breeds = null; } 
		else { BreedByAnimalType.get({id: animalType.id}, function success(result){ $scope.breeds = result; }); }
	}
	
	$scope.savePet = function(pet){
		if(pet){
			if(pet.id){
				Animal.update(pet, function success(){
					$scope.initPageState();
					$scope.loadAll();
				});
			} else {
				pet.user = $scope.currentUser;
				Animal.update(pet, function success(){
					$scope.initPageState();
					$scope.loadAll();
				});
			}
		}
	}
	
	$scope.petLost = function(petId){
		alert("Go nouvelle page remplir un formulaire")
	}
	
	$scope.petFind = function(petId){
		alert("Archiver l'annonce !")
	}
	
	$scope.createNewPet = function(){
		$scope.initPageState();
		$scope.animalTypes = AnimalType.query();
		$scope.pageState.createMode = true;
	}
	
});
