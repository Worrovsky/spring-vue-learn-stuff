<template>
  <v-app>
    <v-main>
      <v-app-bar app></v-app-bar>

      <v-container>
        <v-row justify="center">
          <div>
            <router-link to="/">Home</router-link> |
            <router-link to="/register">Register</router-link> |
            <router-link to="/signin">Sign In</router-link>
          </div>
        </v-row>
        <v-row>
          <router-view></router-view>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>
