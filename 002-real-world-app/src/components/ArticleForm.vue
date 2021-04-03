<template>
  <v-container>
    <v-row>
      <v-col>
        <app-validation-errors v-if="errors" :validation-errors="errors" />
        <v-text-field label="Title" v-model="title"></v-text-field>
        <v-text-field label="Description" v-model="description"></v-text-field>
        <v-textarea label="Body" v-model="body"></v-textarea>
        <v-text-field label="Tags" v-model="tagList"></v-text-field>
      </v-col>
    </v-row>

    <div class="mx-auto d-flex">
      <v-spacer></v-spacer>
      <v-btn x-large color="primary" @click="onSubmit" :disabled="isSubmitting"
        >Publish Article</v-btn
      >
    </div>
  </v-container>
</template>

<script>
import AppValidationErrors from '@/components/ValidationErrors'

export default {
  name: 'AppArticleForm',
  components: {
    AppValidationErrors,
  },
  props: {
    initialValues: {
      type: Object,
      required: true,
    },
    errors: {
      type: Object,
      required: false,
    },
    isSubmitting: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      title: this.initialValues.title,
      description: this.initialValues.description,
      body: this.initialValues.body,
      tagList: this.initialValues.tagList.join(' '),
    }
  },
  methods: {
    onSubmit() {
      const form = {
        title: this.title,
        body: this.body,
        description: this.description,
        tagList: this.tagList.split(' '),
      }
      this.$emit('articleSubmit', form)
    },
  },
}
</script>
