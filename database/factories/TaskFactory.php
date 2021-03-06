<?php

namespace Database\Factories;

use App\Models\Task;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

class TaskFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Task::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'uuid' => $this->faker->uuid,
            'related_id' => 1,
            'related_type' => 'project',
            'status_id' =>1,
            'priority_id' => 1,
            'name' => $this->faker->text(30),
            'description' => $this->faker->text(300),
            'organisation_id' => 1,
            'creator_id' => User::factory(),
            'creator_type' => 'user'
        ];
    }
}
