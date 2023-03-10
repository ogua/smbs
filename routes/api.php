<?php

use App\Http\Controllers\Api\AddItemtocartController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('show-product/{barcode}', [AddItemtocartController::class,'showproduct']);

Route::post('add-items-to-cart', [AddItemtocartController::class,'additem']);

Route::get('show-cart-items/{appreff}', [AddItemtocartController::class,'showcartitems']);

Route::get('get-cart-total/{appreff}', [AddItemtocartController::class,'carttotal']);

Route::get('delete-from-cart/{id}', [AddItemtocartController::class,'delfromcart']);


Route::apiResource('academicyear', AcademicyearController::class);
