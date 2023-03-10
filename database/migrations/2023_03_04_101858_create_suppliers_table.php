<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateSuppliersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('suppliers', function (Blueprint $table) {
            $table->increments('id');
            $table->string('logo')->nullable(); 
            $table->string('name')->nullable();
            $table->string('ref')->nullable(); 
            $table->string('email')->nullable();
            $table->string('zipcode')->nullable(); 
            $table->string('phone')->nullable(); 
            $table->string('website')->nullable(); 
            $table->string('tax')->nullable(); 
            $table->string('address')->nullable();
            $table->string('country')->nullable(); 
            $table->string('towncity')->nullable();
            $table->string('stateprovince')->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('suppliers');
    }
}
