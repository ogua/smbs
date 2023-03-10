<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateSalesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('sales', function (Blueprint $table) {
            $table->increments('id');
            $table->string('billid')->nullable();
            $table->string('appid')->nullable();
            $table->string('product_id')->nullable();
            $table->string('price')->nullable();
            $table->string('qty')->nullable();
            $table->string('dscnt')->nullable();
            $table->string('total')->nullable();
            $table->string('date')->nullable();
            $table->string('status')->default(0);
            $table->string('salefrom')->default('web');
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
        Schema::dropIfExists('sales');
    }
}
