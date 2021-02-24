<template>
  <v-container>
    <!--  -->
    <app-validation-errors
      v-if="validationErrors"
      :validation-errors="validationErrors"
    />
    <!--  -->
    <v-row>
      <v-text-field label="Name" v-model="username"></v-text-field>
    </v-row>
    <v-row>
      <v-text-field
        label="Password"
        v-model="password"
        :type="showPassword ? 'text' : 'password'"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        @click:append="showPassword = !showPassword"
      ></v-text-field>
    </v-row>
    <v-row>
      <v-text-field label="Email" v-model="email"></v-text-field>
    </v-row>
    <v-row justify="end">
      <v-btn color="primary" :disabled="isSubmitting" @click="onSubmit"
        >Submit</v-btn
      >
    </v-row>
  </v-container>
</template>

<script>
import AppValidationErrors from '@/components/ValidationErrors'

import { actionTypes } from '@/store/modules/auth'

export default {
  name: 'AppRegister',
  components: {
    AppValidationErrors,
  },
  computed: {
    isSubmitting() {
      return this.$store.state.auth.isSubmitting
    },
    validationErrors() {
      return this.$store.state.auth.validationErrors
    },
  },
  data() {
    return {
      showPassword: false,
      username: '',
      password: '',
      email: '',
    }
  },
  methods: {
    onSubmit() {
      const credentials = {
        username: this.username,
        password: this.password,
        email: this.email,
      }

      this.$store.dispatch(actionTypes.register, credentials).then((user) => {
        console.log(user)
        this.$router.push({ name: 'home' })
      })
    },
  },
}
</script>

<style></style>
