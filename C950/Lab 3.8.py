import sys, operator, random
from nutrition import Food, MealPlan

# Constants to be used by the greedy algorithm.
NUTRIENT_THRESHOLD = 0.001
FRACTION_THRESHOLD = 0.05
CALORIE_THRESHOLD = 0.1
MAX_CALORIES = 2000


def load_nutrient_data(filename):
    # Open file, read food items one line at a time,
    # create Food objects and append them to a list.
    # Return the list once the entire file is processed.
    fin = open(filename)
    data = []
    if fin.readable():
      for item in fin:
        tmp = item.strip().split(':')
        name = tmp[0]
        tmp2 = str(tmp[1]).split(',')
        protein = float(tmp2[0])
        carbs = float(tmp2[1])
        fat = float(tmp2[2])
        calories = float(tmp2[3])
        data.append(Food(name, protein, fat, carbs, calories))
    fin.close()
    return data

def sort_food_list(foods, nutrient):
    # Sort the food list based on the percent-by-calories of the
    # given nutrient ('protein', 'carbs' or 'fat')
    # The list is sorted in-place; nothing is returned.
    foods.sort(key=operator.attrgetter(nutrient), reverse=True)
    
    
def create_meal_plan(foods, nutrient, goal):
    # A greedy algorithm to create a meal plan that has MAX_CALORIES
    # calories and the goal amount of the nutrient (e.g. 30% protein)
    plan = MealPlan()
    sort_food_list(foods, nutrient)
    for food in foods:
      plan.add_food(food)
      if plan.meets_calorie_limit:
        break
    return plan
       
def print_menu():
    print()
    print("\t1 - Set maximum protein")
    print("\t2 - Set maximum carbohydrates")
    print("\t3 - Set maximum fat")
    print("\t4 - Exit program")
    print()

if __name__ == "__main__":
    # 1. Load the food data from the file (change this to a user
    # prompt for the filename)
    filename = input('Enter name of food data file: ')
    foods = load_nutrient_data(filename)
    
    # 2. Display menu and get user's choice. Repeat menu until a
    # valid choice is entered by the user (1-4, inclusive).
    print_menu()
    choice = 0
    while True:
      try:
        choice = int(input('Enter choice (1-4): '))
        if choice < 1 or choice > 4:
          print('Invalid entry. Try again.')
          continue
      except ValueError:
        print('Invalid entry. Try again.')
      else:
        break
    
    # 3. Prompt user for goal nutrient percent value. Repeat prompt
    # until a valid choice is entered by the user (0-100, inclusive)
    if choice == 1:
      nutrient = 'protein'
    elif choice == 2:
      nutrient = 'carbs'
    elif choice == 3:
      nutrient = 'fat'
    elif choice == 4:
      sys.exit(0)

    goal = -1
    while True:
      try:
        goal = float(input(f'What percentage of calories from {nutrient} is the goal? '))
        if goal < 0 or goal > 100:
          print('Invalid entry. Try again.')
          continue
      except ValueError:
        print('Invalid entry. Try again.')
      else:
        break

    # 4. Run greedy algorithm to create the meal plan.
    plan = create_meal_plan(foods, nutrient, goal)
    
    # 5. Display plan.
    print(plan)
