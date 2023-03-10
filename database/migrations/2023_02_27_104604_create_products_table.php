<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateProductsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('products', function (Blueprint $table) {
            $table->increments('id');
            $table->string('img')->nullable();
            $table->string('Barcode')->nullable();
            $table->string('name')->nullable();
            $table->string('ptype')->nullable();
            $table->string('manufacture')->nullable();
            $table->string('qty')->nullable();
            $table->string('amnt')->nullable();
            $table->string('dscnt')->nullable();
            $table->text('desc')->nullable();
            $table->string('expiredate')->nullable();
            $table->string('date')->nullable();
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
        Schema::dropIfExists('products');
    }
}
